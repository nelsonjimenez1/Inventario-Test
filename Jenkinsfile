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
