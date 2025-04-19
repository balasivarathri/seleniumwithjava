pipeline
{
	agent any

    tools{
		maven 'maven'
        }

    stages
    {
		stage('Dev')
        {
			steps
            {
				echo("deploy to SIT")
            }
        }

        stage("SIT"){
			steps
            {
				echo("deploy to UAT")
            }
        }

        stage('UAT') {
			steps {
				catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
					git 'https://github.com/balasivarathri/seleniumwithjava.git'
                    bat "mvn clean install"
                    echo("deploy to PROD")
                }
            }
        }
        stage('Publish HTML Report'){
			steps{
				publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false,
                                  keepAll: true,
                                  reportDir: 'target/cucumber-html-reports',
                                  reportFiles: 'overview-features.html',
                                  reportName: 'Customized HTML Report',
                                  reportTitles: ''])
            }
        }
    }
}
