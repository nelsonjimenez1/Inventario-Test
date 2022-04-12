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
                dir("/inventor")
                {
                    echo 'Build App 2'
                } 
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
