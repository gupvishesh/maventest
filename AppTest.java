package com.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName ) ;
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class  );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() 
    {
        assertTrue( true );
    }

    /**
    Week 4: Maven-creation 
Open Eclipse, click on file->click new->maven project->filter->quickstart(java),webapp(web application)->org.apache.maven.archetypes
put some artifactid and then next, 
click on project->run as->maven build->goals-> clean install test
then run as java application or run on server 
right click->show in local terminal->git bash 
-----------------------------------------------------
Question: Create a Docker Compose setup with a Node.js app and MongoDB.
app.js
const express = require('express');
const { MongoClient } = require('mongodb');
const app = express();
const port = 3000;
const uri = "mongodb://mongo:27017/testdb";
app.get('/', async (req, res) => {
    try {
        const client = new MongoClient(uri);
        await client.connect();
        const db = client.db("testdb");
        const count = await db.collection("items").countDocuments();
        res.send(`Connected to MongoDB. Documents count: ${count}`);
        await client.close();
    } catch (err) {
        res.send("Error connecting to MongoDB: " + err);
    }
});
app.listen(port, () => {
    console.log(`Server running on port ${port}`);
});

dockerfile
FROM node:18
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD ["node", "app.js"]

docker-compose.yml
version: "3.9"
services:
  app:
    build: .
    container_name: node-app
    ports:
      - "3000:3000"
    depends_on:
      - mongo
    environment:
      - MONGO_URL=mongodb://mongo:27017/testdb
  mongo:
    image: mongo:6
    container_name: mongo-db
    restart: always
    volumes:
      - mongo_data:/data/db
    ports:
      - "27017:27017"
volumes:
  mongo_data:

command: docker-compose up --build
----------------------------------------------------------------------------------------------------------------------------------------------
Question: Write the Docker file and create the image and access the image in localhost

app.js
const http = require('http');
const server = http.createServer((req, res) => {
  res.end("Docker image is working on localhost!");
});
server.listen(3000, () => {
  console.log("Server running on port 3000");
});

dockerfile
FROM node:18
WORKDIR /app
COPY package*.json ./
COPY app.js ./
RUN npm install
EXPOSE 3000
CMD ["node", "app.js"]

commands: 
docker build -t my-node-app .
docker run -d -p 3000:3000 --name mycontainer my-node-app
docker stop mycontainer
-----------------------------------------------------------------------------------------------------------------------------------------------------------------
WEEK 8 — Jenkins Freestyle Jobs (VERY BEGINNER VERSION)
Goal: Learn how to run Java & Web projects automatically using Jenkins.
________________________________________
PART 1 — Install & Open Jenkins
1.	Open your browser.
2.	Type: http://localhost:8080
You will see the Jenkins dashboard.
________________________________________
 PART 2 — Create Maven Java Build Job (MavenJava_Build)
Click “New Item”
•	Left side → first option.
Give a name
Example:
MavenJava_Build
Select:
Freestyle Project
Click OK.
________________________________________
Fill Project Details
Inside Configuration page:
Description:
Java Build demo
________________________________________
Add GitHub Code
Scroll to:
✔ Source Code Management → Choose Git
You will see a box:
Repository URL
Paste your Maven Java GitHub link here (example):
https://github.com/someone/maven-java-demo.git
________________________________________
Build Steps (Very Important)
Scroll to Build section → click:
Add Build Step → Invoke top-level Maven targets
You will now add 2 steps:
STEP A
•	Maven version: select your configured Maven (example: MAVEN_HOME)
•	Goals: clean
STEP B (again click “Add Build Step”)
•	Goals: install
________________________________________
Post-Build Actions
Scroll down → Click:
Add post-build action → Archive the artifacts
Files to archive: 
Then again:
Add post-build action → Build other projects
Enter: MavenJava_Test
Choose:
•	Trigger only if build is stable
Save the job
Click Save at bottom.
________________________________________
PART 3 — Create MavenJava_Test Job
 Click “New Item”
Name: MavenJava_Test
Select Freestyle → OK.
________________________________________
Description
Test demo
________________________________________
Build Environment
Scroll → tick:
✔ Delete workspace before build starts
Why?
It removes old files so test always runs fresh.
________________________________________
Copy the build output from previous job
Build Steps → Add Build Step → Copy artifacts from another project.
Fill:
•	Project name → MavenJava_Build
•	Build → Stable build only
•	Artifacts to copy: 
________________________________________
Add Test Step
Add Build Step → Invoke top-level Maven targets
•	Goals: test
________________________________________
Archive test results
Add Post Build Action → Archive artifacts
Files: **
Click Save
________________________________________
PART 4 — Create Pipeline View
Steps:
1.	On Jenkins dashboard, click “+” beside “All”
2.	Name: MavenJava_Pipeline
3.	Select: Build Pipeline View
4.	Pipeline flow:
o	Initial job → MavenJava_Build
5.	Save.
________________________________________
PART 5 — Run
1.	Open pipeline view
2.	Click “Run”
3.	Green = success
4.	Click boxes → open console
________________________________________
Maven Java part DONE!
________________________________________
PART 6 — Repeat SAME for Maven Web Project
You will create 3 jobs:
1.	MavenWeb_Build
2.	MavenWeb_Test
3.	MavenWeb_Deploy
And pipeline.
The steps are exactly same as Java — except the deploy job:
In MavenWeb_Deploy:
•	Copy artifacts from test job
•	Add Post-build Action → Deploy WAR/EAR to container
•	WAR file: **
•	Add Tomcat 9 remote server
o	username: admin
o	password: 1234
o	URL: http://localhost:8085/
Done.
________________________________________
WEEK 9 — Scripted Pipeline (BEGINNER VERSION)
Goal: Create ONE Jenkins job using Pipeline script.
________________________________________
1️Click “New Item”
Name: ScriptedPipeline
Choose: Pipeline
Click OK.
________________________________________
2️Scroll down to Pipeline section
Under Definition, select:
Pipeline script
Build triggers: Poll SCM
________________________________________
Paste the script

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
________________________________________
4️Save
________________________________________
5️Run
Click Build now.
Take screenshots of:
•	Build stages
•	Console output
Done.
________________________________________
WEEK 10 — Minikube, Kubernetes, Nagios, AWS (BEGINNER VERSION)
________________________________________
PART 1 — Minikube
1️Start Minikube
Open CMD or PowerShell:
minikube start
Create an nginx server
kubectl create deployment mynginx --image=nginx
Check:
kubectl get pods
Expose the deployment
kubectl expose deployment mynginx --type=NodePort --port=80 --target-port=80
Scale to 4 pods
kubectl scale deployment mynginx --replicas=4
->Port forwarding: kubectl port-forward svc/mynginx 8081:80
8081 can be replaced by any
->Kubernets dashboard 
Minikube dashboard
->Stopping
kubectl delete deployment mynginx
kubectl delete service mynginx
minikube stop
________________________________________
⭐ PART 2 — Nagios in Docker
Pull image
docker pull jasonrivers/nagios:latest
Run Nagios
docker run --name nagiosdemo -p 8888:80 jasonrivers/nagios:latest
Open browser
Go to:
http://localhost:8888
Login:
•	username: nagiosadmin
•	password: nagios
stopping: docker stop nagiosdemo
________________________________________
WEEK 11 — GitHub Webhook + Jenkins Email (SUPER SIMPLE)
________________________________________
PART 1 — Install ngrok
Run in CMD:
ngrok http 8080
You will see:
https://abc123.ngrok.io (just for example)
Copy this.
________________________________________
PART 2 — Add Webhook in GitHub
1.	Open your GitHub repo → Settings → Webhooks
2.	Click Add Webhook
3.	Payload URL:
https://abc123.ngrok.io/github-webhook/ (make sure to add /github-webhook/ at the end of your ngrok url)
4.	Content type: application/json
5.	Events: ✔ Just the push event
6.	Add webhook
________________________________________
PART 3 — Configure Jenkins job
Open your job → Configure → Build Triggers
✔ Tick: GitHub hook trigger for GITScm polling
Save.
________________________________________
PART 4 — Test webhook
1.	Make any change to your GitHub project
2.	Push the code
3.	Jenkins WILL automatically start building
DONE.
________________________________________
PART 5 — Email Notification Setup (Beginner version)
Step 1 — Setup Gmail App Password
1.	Open Google Account → Security
2.	Turn on 2-step verification
3.	Create App Password → choose “Other”
4.	Copy the 16-character password
________________________________________
Step 2 — Jenkins Email settings
In Jenkins:
•	Manage Jenkins → Configure System
•	Scroll to “E-mail Notification”
•	Fill:
SMTP Server → smtp.gmail.com
Use SMTP Auth → ✔
Username → your Gmail
Password → (your app password) (16-character password that you got)
Use SSL → ✔
Port → 465
Click Send test email
If received → success.
________________________________________
PART 6 — Add email to Job
Open your job → Configure → Post-build actions:
Add:
Editable Email Notification
Add recipients → Save → Build.
DONE.
________________________________________
WEEK 12 — Deploying App in AWS EC2 Using Docker
________________________________________
PART 1 — Create EC2 instance
1.	AWS → Start Lab and click on the green-dot → EC2 → Launch Instance
2.	Name: ubuntu
3.	AMI: Ubuntu Server 22.04 LTS (Free tier)
4.	Instance type: t2.micro
5.	Create new key pair → download .pem file
6.	Security group:
✔ SSH (22)
✔ HTTP (80)
7.	Launch instance.
________________________________________
PART 2 — Connect to EC2
Go to instance → Click Connect → SSH tab
Copy:
ssh -i "your-key.pem" ubuntu@<your-public-ip>
Paste into PowerShell.
You are now inside your remote Ubuntu machine.
________________________________________
PART 3 — Install 
Run:
sudo apt update
sudo apt-get install docker.io
sudo apt install git
sudo apt install nano
________________________________________
Create a html file and push into git or what ever is given 
PART 4 — Clone your project
git clone <your GitHub link>
cd <project folder>
________________________________________
PART 5 — Create Dockerfile
Run:
nano Dockerfile
Paste:
FROM nginx:alpine
COPY . /usr/share/nginx/html
(For maven WEB project)-> docker file code 
FROM tomcat:9-jdk11
COPY target/*.war/usr/local/tomcat/webapps
Save:
•	CTRL + O → Enter
•	CTRL + X
________________________________________
PART 6 — Build Docker image
sudo docker build -t mywebapp .
________________________________________
PART 7 — Run the container
sudo docker run -d -p 80:80 mywebapp
________________________________________
PART 8 — Open your website
Copy your EC2 public IP
Paste in browser → Your website appears 🎉
->Stopping 
sudo docker ps -> gives container id 
sudo docker stop <container id>



# ===========================
# GIT ESSENTIAL COMMANDS
# ===========================

# --- Setup ---
git --version                                 # check git installed
git config --global user.name "Your Name"     # set username
git config --global user.email "you@mail.com" # set email
git config --list                              # view configs

# --- Create / Connect Repo ---
git init                                      # initialize repo in folder
git clone <url>                               # clone remote repo
git remote -v                                 # show remotes
git remote add origin <url>                   # add remote
git remote remove origin                      # remove remote
git remote rename origin upstream             # rename remote
git remote set-url origin <new-url>           # change remote url
git remote show origin                        # detailed remote info

# --- Staging & Committing ---
git status                                    # show changed files
git add file.txt                              # stage single file
git add .                                     # stage all changes
git commit -m "message"                       # commit staged changes
git restore file.txt                          # discard unstaged changes
git reset file.txt                            # unstage file
git commit --amend -m "New msg"               # edit last commit message
git revert <commit>                           # undo commit safely

# --- Branching ---
git branch                                    # list local branches
git branch -a                                 # list local + remote branches
git branch new-branch                         # create branch
git checkout new-branch                       # switch branch
git checkout -b feature-x                     # create + switch branch
git branch -d old-branch                      # delete merged branch
git branch -D force-delete                    # force delete branch
git branch -r                                 # list remote branches
git remote prune origin                       # remove deleted remote refs

# --- Merging / Rebasing ---
git checkout main                             # switch to main
git merge feature-x                           # merge branch into main
git fetch origin                              # get latest from remote
git rebase origin/main                        # rebase local branch

# --- Pushing / Pulling ---
git push origin main                          # push commits
git pull origin main                          # pull + merge
git fetch origin                              # fetch only, no merge
git fetch origin branch                       # fetch specific branch
git push --set-upstream origin feature-x      # link local branch to remote

# --- History ---
git log                                       # full log
git log --oneline                             # short log
git diff                                      # changes not staged
git diff --staged                             # staged changes
git show <commit>                             # show commit details
git blame file.txt                            # who changed each line

# --- Stash ---
git stash                                     # save work temporarily
git stash apply                               # restore stashed work

# --- Recovery ---
git reflog                                    # view all actions (lifesaver)
git checkout -b recover-branch <commit>       # restore deleted branch

# --- Delete multiple branches ---
git branch -d br1 br2 br3                     # delete branches at once

# --- .gitignore ---
# Create .gitignore file:
# node_modules/
# *.log
# *.bak
# .env

# --- Sensitive file removal from history ---
git filter-branch --force --index-filter \
"git rm --cached --ignore-unmatch secrets.txt" \
--prune-empty --tag-name-filter cat -- --all

# ===========================
# END OF MASTER CHEATSHEET
# ===========================
    */
}
