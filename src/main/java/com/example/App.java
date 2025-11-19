package com.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        /*
        ✅ WEEK 8 — Jenkins Freestyle Jobs (VERY BEGINNER VERSION)
Goal: Learn how to run Java & Web projects automatically using Jenkins.

⭐ PART 1 — Install & Open Jenkins
1.Open your browser.
2.Type: http://localhost:8080
You will see the Jenkins dashboard.

⭐ PART 2 — Create Maven Java Build Job (MavenJava_Build)
1️⃣ Click “New Item”
Left side → first option.
2️⃣ Give a name
Example:
MavenJava_Build
Select:
Freestyle Project
Click OK.

3️⃣ Fill Project Details
Inside Configuration page:
Description:
Java Build demo

4️⃣ Add GitHub Code
Scroll to:
✔ Source Code Management → Choose Git
You will see a box:
Repository URL
Paste your Maven Java GitHub link here (example):
https://github.com/someone/maven-java-demo.git

5️⃣ Build Steps (Very Important)
Scroll to Build section → click:
▶ Add Build Step → Invoke top-level Maven targets
You will now add 2 steps:
STEP A
Maven version: select your configured Maven (example: MAVEN_HOME)
Goals: clean
STEP B (again click “Add Build Step”)
Goals: install

6️⃣ Post-Build Actions
Scroll down → Click:
▶ Add post-build action → Archive the artifacts
Files to archive: 
Then again:
▶ Add post-build action → Build other projects
Enter: MavenJava_Test
Choose:
Trigger only if build is stable
7️⃣ Save the job
Click Save at bottom.

⭐ PART 3 — Create MavenJava_Test Job
1️⃣ Click “New Item”
Name: MavenJava_Test
Select Freestyle → OK.

2️⃣ Description
Test demo

3️⃣ Build Environment
Scroll → tick:
✔ Delete workspace before build starts
Why?
It removes old files so test always runs fresh.

4️⃣ Copy the build output from previous job
Build Steps → Add Build Step → Copy artifacts from another project.
Fill:
Project name → MavenJava_Build
Build → Stable build only
Artifacts to copy: 

5️⃣ Add Test Step
Add Build Step → Invoke top-level Maven targets
Goals: test

6️⃣ Archive test results
Add Post Build Action → Archive artifacts
Files: 
Click Save

⭐ PART 4 — Create Pipeline View
Steps:
1.On Jenkins dashboard, click “+” beside “All”
2.Name: MavenJava_Pipeline
3.Select: Build Pipeline View
4.Pipeline flow:
oInitial job → MavenJava_Build
5.Save.

⭐ PART 5 — Run
1.Open pipeline view
2.Click “Run”
3.Green = success
4.Click boxes → open console

✨ Maven Java part DONE!

⭐ PART 6 — Repeat SAME for Maven Web Project
You will create 3 jobs:
1.MavenWeb_Build
2.MavenWeb_Test
3.MavenWeb_Deploy
And pipeline.
The steps are exactly same as Java — except the deploy job:
In MavenWeb_Deploy:
Copy artifacts from test job
Add Post-build Action → Deploy WAR/EAR to container
WAR file: .war
Add Tomcat 9 remote server
ousername: admin
opassword: 1234
oURL: http://localhost:8085/
Done.

✅ WEEK 9 — Scripted Pipeline (BEGINNER VERSION)
Goal: Create ONE Jenkins job using Pipeline script.

1️⃣ Click “New Item”
Name: ScriptedPipeline
Choose: Pipeline
Click OK.

2️⃣ Scroll down to Pipeline section
Under Definition, select:
Pipeline script

3️⃣ Paste the script

pipeline {
agent any
   tools{
        maven 'MAVEN-HOME'
    }
    stages {
        stage('git repo & clean') {
            steps {
                bat "git clone <provide your github link>"
                bat "mvn clean -f mavenjava"
            }
        }
        stage('install') {
            steps {
                bat "mvn install -f mavenjava"
            }
        }
        stage('test') {
            steps {
                bat "mvn test -f mavenjava"
            }
        }
        stage('package') {
            steps {
                bat "mvn package -f mavenjava"
            }
        }
    }
}

Change:
git clone <paste your GitHub URL>
And change “mavenjava” to your folder name if needed.

4️⃣ Save

5️⃣ Run
Click Build now.
Take screenshots of:
Build stages
Console output
Done.

✅ WEEK 10 — Minikube, Kubernetes, Nagios, AWS (BEGINNER VERSION)

⭐ PART 1 — Minikube
1️⃣ Start Minikube
Open CMD or PowerShell:
minikube start
2️⃣ Create an nginx server
kubectl create deployment mynginx --image=nginx
Check:
kubectl get pods
3️⃣ Expose the deployment
kubectl expose deployment mynginx --type=NodePort --port=80 --target-port=80
4️⃣ Scale to 4 pods
kubectl scale deployment mynginx --replicas=4
->Port forwarding: kubectl port-forward svc/mynginx 8081:80
8081 can be replaced by any
->Kubernets dashboard 
Minikube dashboard
->Stopping
kubectl delete deployment mynginx
kubectl delete service mynginx
minikube stop

⭐ PART 2 — Nagios in Docker
1️⃣ Pull image
docker pull jasonrivers/nagios:latest
2️⃣ Run Nagios
docker run --name nagiosdemo -p 8888:80 jasonrivers/nagios:latest
3️⃣ Open browser
Go to:
http://localhost:8888
Login:
username: nagiosadmin
password: nagios
stopping: docker stop nagiosdemo

✅ WEEK 11 — GitHub Webhook + Jenkins Email (SUPER SIMPLE)

⭐ PART 1 — Install ngrok
Run in CMD:
ngrok http 8080
You will see:
https://abc123.ngrok.io (just for example)
Copy this.

⭐ PART 2 — Add Webhook in GitHub
1.Open your GitHub repo → Settings → Webhooks
2.Click Add Webhook
3.Payload URL:
https://abc123.ngrok.io/github-webhook/ (make sure to add /github-webhook/ at the end of your ngrok url)
4.Content type: application/json
5.Events: ✔ Just the push event
6.Add webhook

⭐ PART 3 — Configure Jenkins job
Open your job → Configure → Build Triggers
✔ Tick: GitHub hook trigger for GITScm polling
Save.

⭐ PART 4 — Test webhook
1.Make any change to your GitHub project
2.Push the code
3.Jenkins WILL automatically start building
DONE.

⭐ PART 5 — Email Notification Setup (Beginner version)
Step 1 — Setup Gmail App Password
1.Open Google Account → Security
2.Turn on 2-step verification
3.Create App Password → choose “Other”
4.Copy the 16-character password

Step 2 — Jenkins Email settings
In Jenkins:
Manage Jenkins → Configure System
Scroll to “E-mail Notification”
Fill:
SMTP Server → smtp.gmail.com
Use SMTP Auth → ✔
Username → your Gmail
Password → (your app password) (16-character password that you got)
Use SSL → ✔
Port → 465
Click Send test email
If received → success.

⭐ PART 6 — Add email to Job
Open your job → Configure → Post-build actions:
Add:
Editable Email Notification
Add recipients → Save → Build.
DONE.

✅ WEEK 12 — Deploying App in AWS EC2 Using Docker

⭐ PART 1 — Create EC2 instance
1.AWS → Start Lab and click on the green-dot → EC2 → Launch Instance
2.Name: ubuntu
3.AMI: Ubuntu Server 22.04 LTS (Free tier)
4.Instance type: t2.micro
5.Create new key pair → download .pem file
6.Security group:
✔ SSH (22)
✔ HTTP (80)
7.Launch instance.

⭐ PART 2 — Connect to EC2
Go to instance → Click Connect → SSH tab
Copy:
ssh -i "your-key.pem" ubuntu@<your-public-ip>
Paste into PowerShell.
You are now inside your remote Ubuntu machine.

⭐ PART 3 — Install 
Run:
sudo apt update
sudo apt-get install docker.io
sudo apt install git
sudo apt install nano

Create a html file and push into git or what ever is given 
⭐ PART 4 — Clone your project
git clone <your GitHub link>
cd <project folder>

⭐ PART 5 — Create Dockerfile
Run:
nano Dockerfile
Paste:
FROM nginx:alpine
COPY . /usr/share/nginx/html
(For maven WEB project)-> docker file code 
FROM tomcat:9-jdk11
COPY target/*.war/usr/local/tomcat/webapps
Save:
CTRL + O → Enter
CTRL + X

⭐ PART 6 — Build Docker image
sudo docker build -t mywebapp .

⭐ PART 7 — Run the container
sudo docker run -d -p 80:80 mywebapp

⭐ PART 8 — Open your website
Copy your EC2 public IP
Paste in browser → Your website appears 🎉
->Stopping 
sudo docker ps -> gives container id 
sudo docker stop <container id>
 */
    }
}
