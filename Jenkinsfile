def appName = ['dev': 'Movee', 'qa': 'Movee', 'prd': 'Movee-Prod',]

pipeline {
    agent any

    environment {
        GITHUB_USER = 'adessoTurkey'
        GITHUB_REPO = 'Movee'
        GITHUB_API_TOKEN = credentials('f67cd5f9-11ce-455e-a48d-a8d048ddc777')
        APPCENTER_API_TOKEN = credentials('82e52672-f736-429e-94c0-3e43cd209e85')
        TEAMS_WEBHOOK_URL = credentials('d1905c64-b187-4fc1-95bd-783b58679171')
        KEYSTORE_PATH = credentials('44782c53-3489-49d2-a84d-262041cc9ead')
    }

    triggers {
        pollSCM('H/15 * * * *')
    }

    stages {
        stage('Copy Credentials') {
            steps {
                fileOperations([
                        folderCopyOperation(
                                sourceFolderPath: '/Users/xcodeserver/keystores/adessoturkey/movee',
                                destinationFolderPath: '.'
                        )
                ])
            }
        }
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                sh "./gradlew clean"
            }
        }

        stage('Static Analysis') {
            steps {
                sh './gradlew detekt ktlint lintDevDebug spotlessCheck'
            }
        }

        stage('Unit Test') {
            when {
                not {
                    branch 'release/*'
                }
            }
            steps {
                sh './gradlew testDevDebugUnitTest'
            }
        }

        stage('Assemble') {
            steps {
                sh './gradlew assembleDevRelease'
            }
        }

        stage('Publish') {
            when {
                branch 'develop/*'
            }
            steps {
                uploadToAppCenterArchiveArtifacts(
                        appName.dev,
                        'app/build/outputs/apk/dev/release/*.apk'
                )
            }
        }
    }

    post {
        always {
            notifyMicrosoftTeams()
            deleteDir()
        }
    }
}

def uploadToAppCenterArchiveArtifacts(appName, pathToApp) {
    archiveArtifacts(pathToApp)

    appCenter apiToken: env.APPCENTER_API_TOKEN,
            ownerName: 'adesso-Turkey',
            appName: appName,
            pathToApp: pathToApp,
            distributionGroups: 'adesso'
}

def notifyMicrosoftTeams() {
    message = "Job: ${env.JOB_NAME} " +
            "with build number ${env.BUILD_NUMBER} " +
            "was ${currentBuild.currentResult}"
    echo message
    office365ConnectorSend webhookUrl: env.TEAMS_WEBHOOK_URL,
            message: message,
            status: currentBuild.currentResult
}
