// NOTE: this buildfile will only work on windows - basically you should only need to change "bat" to "sh" and make
//  some minor win/linux-shell-difference-related adjustments to make it work on linux.
node {
    checkout scm

    stage('Build') {
        try {
            bat './gradlew clean build' 
            archiveArtifacts artifacts: 'build/libs/testing.jar', fingerprint: true 
        } finally {
            archiveArtifacts artifacts: 'build/reports/tests/test/*', fingerprint: false
        }
    }

    stage('IntegrationTest') {
        try {
    	   bat './gradlew slowTests' 
        } finally {
            archiveArtifacts artifacts: 'build/reports/tests/slowTests/*', fingerprint: false 
        }
    }

    stage('BuildImage') {
        // simply copying because referencing the full path caused some issues
        bat 'copy build\\libs\\testing.jar other\\testserver\\'
		withEnv(["JAR_FILE=testing.jar"]) {
			bat 'cd other/testserver && docker rmi -f temp-testing & docker build -t temp-testing .'
		}
    }

    stage('StartTestServer') {
		bat 'cd other/testserver && docker-compose down & docker-compose up -d'
    }

    stage('UiTest') {
        // quick&dirty sleep to wait for environment to come up
        // would of course be cleaner to use the wait-for script to wait for port to become active
        try {
		  bat 'ping 127.0.0.1 -n 60 > nul & set TESTING_URL=http://localhost:1235/ & gradlew endToEndTests'
        } finally {
            archiveArtifacts artifacts: 'build/reports/tests/endToEndTests/*', fingerprint: false 
        }
    }

    stage('CleanupTestServer') {
        bat 'cd other/testserver && docker-compose down & docker-compose rm -f'
    }
}