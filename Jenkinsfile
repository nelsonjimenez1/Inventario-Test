pipeline 
{
    agent any

    stages 
    {
        stage('Build') 
        {
            steps 
            {
                echo 'Build App'
                dir('C:/ProgramData/Jenkins/.jenkins/workspace/pipeline/inventory')
                {
                    bat 'mvn clean install'
                } 
            }
            steps 
            {
                echo 'Scan src'
                bat 'sonar-scanner' 
            }
        }
    }

    post
    {

    	always
    	{
    		emailext body: 'Summary', subject: 'Pipeline Status', to: 'jimeneznelsondavid1@gmail.com'
    	}

    }
}
