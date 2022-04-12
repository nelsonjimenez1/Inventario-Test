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
                bat 'cd inventor'
                bat 'mvn clean install'
            }
        }

        stage('Test') 
        {
            steps 
            {
                echo 'Test App'
            }
        }

        stage('Deploy') 
        {
            steps 
            {
                echo 'Deploy App'
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
