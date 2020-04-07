def appName = ['dev':'Movee', 'qa':'Movee', 'prd':'Movee-Prod', ]

pipeline {
    agent {
        any
    }
    environment {
        GITHUB_USER='adessoTurkey'
        GITHUB_REPO='Movee'
        GITHUB_API_TOKEN = credentials('f67cd5f9-11ce-455e-a48d-a8d048ddc777')
        APPCENTER_API_TOKEN = credentials('82e52672-f736-429e-94c0-3e43cd209e85')
    }
    triggers {
        pollSCM('H/10 * * * *')
    }

    stages {
        stage('Checkout') {
            checkout scm
        }

        stage('Clean') {
            sh "./gradlew clean"
        }

        stage('Static Analysis') {
            sh './gradlew detekt ktlint lintDebug spotlessCheck'
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
            sh './gradlew assembleDevRelease'
        }

        stage('Publish') {
            when {
                branch 'develop/*'
            }
            steps {
                pathToApp = 'app/build/outputs/apk/dev/release/*.apk'
                uploadToAppCenter(appName.dev, pathToApp)
            }
        }
    }

    post {
        always {
            echo "Job: ${env.JOB_NAME} with buildnumber ${env.BUILD_NUMBER} was ${currentBuild.currentResult}"
            cleanWs()
        }
    }
}

def uploadToAppCenter(appName, pathToApp) {
    appCenter apiToken: APPCENTER_API_TOKEN,
            ownerName: 'adesso-Turkey',
            appName: appName,
            pathToApp: pathToApp,
            distributionGroups: 'adesso'
}
