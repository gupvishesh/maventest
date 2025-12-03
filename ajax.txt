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
WEEK 2

Maven Java Project Creation Steps
Open Eclipse IDE for Enterprise Java and Web Developers.
File > New > Maven Project.
Filter: quickstart; select org.apache.maven.archetypes maven-archetype-quickstart 1.4; Next.
Group ID: e.g., SE or com.example; Artifact ID: e.g., MavenJava; Next.
Confirm artifacts in console; press Y to proceed.
Project created with pom.xml, src/main/java/App.java (Hello World sample).
Right-click App.java > Run As > Maven Clean.
Right-click App.java > Run As > Maven Install.
Right-click App.java > Run As > Maven Test.
Right-click App.java > Run As > Maven Build; Goals: clean install test; Apply > Run.
Check console for BUILD SUCCESS.
Right-click App.java > Run As > Java Application; Output: Hello World.


Maven Web Project Creation Steps
Open Eclipse IDE.
File > New > Maven Project.
Filter: webapp; select org.apache.maven.archetypes maven-archetype-webapp 1.4; Next.
Group ID: e.g., SE or com.example; Artifact ID: e.g., MavenWeb; Next.
Confirm artifacts in console; press Y.
Project created with pom.xml, src/main/webapp/index.jsp (Hello World sample).
Open mvnrepository.com; search Java Servlet API; copy latest dependency; paste in pom.xml.
Window > Show View > Servers; Add Tomcat v9.0 or v11.0; Configure.
Double-click server; Set Server Locations to Use Tomcat Installation; Modify ports (Admin: 0, HTTP/1.1: 8085).
Edit tomcat-users.xml: Add role and user (e.g., admin-gui, manager-gui; username: admin, password: 1234).
Right-click index.jsp > Run As > Maven Clean.
Right-click index.jsp > Run As > Maven Install.
Right-click index.jsp > Run As > Maven Test.
Right-click index.jsp > Run As > Maven Build; Goals: clean install test; Apply > Run.
Check console for BUILD SUCCESS.
Right-click index.jsp > Run As > Run on Server; Select Tomcat; Finish.
Output: Hello World webpage in browser.


POM.XML
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>SELAB2</groupId>
  <artifactId>WEB1</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>war</packaging>

  <name>WEB1 Maven Webapp</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.13.1</version>
      <scope>test</scope>
    </dependency>
    
    <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
	<dependency>
	    <groupId>javax.servlet</groupId>
	    <artifactId>javax.servlet-api</artifactId>
	    <version>4.0.1</version>
	    <scope>provided</scope>
	</dependency>
  </dependencies>

  <build>
    <finalName>WEB1</finalName>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.4.0</version>
        </plugin>
        <!-- see http://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_war_packaging -->
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.3.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.13.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>3.3.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-war-plugin</artifactId>
          <version>3.4.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-install-plugin</artifactId>
          <version>3.1.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>3.1.2</version>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
</project>



REDIS
Pull the redis image
docker pull redis
Run a Redis container in background
docker run --name my-redis -d redis
List running containers
docker ps
Access the Redis CLI inside the container
docker exec -it my-redis redis-cli
(Inside redis-cli) Set and get a key
SET name "Alice" → GET name → exit
Stop the container
docker stop my-redis
Restart the stopped container
docker start my-redis
Remove the container (must be stopped first)
docker rm my-redis
Remove the redis image
docker rmi redis

Working with Dockerfile & Custom Image

Create project folder and navigate into it
Windows: C:\DockerProjects\Redis → Git Bash: cd /c/DockerProjects/Redis
Mac/Linux: mkdir ~/DockerProjects/Redis && cd ~/DockerProjects/Redis
Create a file named Dockerfile (no extension) with content:textFROM redis:latest
CMD ["redis-server"]
Build custom image named redisnew
docker build -t redisnew .
Run container from the new image
docker run --name myredisnew -d redisnew
Check running containers
docker ps
Stop the container
docker stop myredisnew
Login to Docker Hub
docker login
List all containers (including stopped)
docker ps -a
Commit (save) the running/stopped container as a new image
docker commit <container-id> budarajumadhurika/redis1
(replace container-id with actual ID of myredisnew)
List all images (you will see budarajumadhurika/redis1)
docker images
Push the custom image to Docker Hub
docker push budarajumadhurika/redis1
Remove the container
docker rm <container-id>
Remove the local custom image
docker rmi budarajumadhurika/redis1
Check containers again
docker ps -a
Logout from Docker Hub
docker logout
Pull your own uploaded image
docker pull budarajumadhurika/redis1
Run a new container from your uploaded image
docker run --name myredis -d budarajumadhurika/redis1
Enter the container and open redis-cli
docker exec -it myredis redis-cli
(Inside redis-cli) Test persistence
SET name "Abcdef" → GET name → exit
List all containers
docker ps -a
Stop the container
docker stop myredis
Remove the container
docker rm <container-id>
List images again
docker images
Remove your custom image again
docker rmi budarajumadhurika/redis1
(Optional) Logout from Docker Hub
docker logout


DOCKER COMPOSE
Step-by-Step (Exactly as in the PDF)

Create a folder named compose-lab
Inside the folder, create a file named docker-compose.yml
Task 1 – Paste this exact content:

YAMLversion: "3.9"
services:
  web:
    image: nginx:latest
    ports:
      - "8080:80"

  db:
    image: postgres:15
    environment:
      POSTGRES_USER: demo
      POSTGRES_PASSWORD: demo
      POSTGRES_DB: demo_db

Run:

Bashdocker compose up -d

Open browser → http://localhost:8080
→ You see Nginx welcome page
Task 2 – Edit docker-compose.yml → Add Redis + depends_on (final file becomes):

YAMLversion: "3.9"
services:
  web:
    image: nginx:latest
    ports:
      - "8080:80"
    depends_on:
      - redis

  db:
    image: postgres:15
    environment:
      POSTGRES_USER: demo
      POSTGRES_PASSWORD: demo
      POSTGRES_DB: demo_db

  redis:
    image: redis:alpine

Restart:

Bashdocker compose up -d

Check:

Bashdocker compose ps
→ You see 3 services running: web, db, redis

Task 4 – Replace entire docker-compose.yml with this final version (adds network + volume):

YAMLversion: "3.9"

networks:
  app-net:

volumes:
  db-data:

services:
  web:
    image: nginx:latest
    ports:
      - "8080:80"
    networks:
      - app-net
    depends_on:
      - db

  db:
    image: postgres:15
    environment:
      POSTGRES_USER: demo
      POSTGRES_PASSWORD: demo
      POSTGRES_DB: demo_db
    volumes:
      - db-data:/var/lib/postgresql/data
    networks:
      - app-net

Bring up again:

Bashdocker compose up -d

Insert data into Postgres (to prove persistence):

Bashdocker exec -it compose-lab-db-1 psql -U demo -d demo_db
Inside psql, run:
SQLCREATE TABLE users (id SERIAL PRIMARY KEY, name VARCHAR(50));
INSERT INTO users (name) VALUES ('Alice'), ('Bob');
SELECT * FROM users;
\q

Stop everything:

Bashdocker compose down

Start again:

Bashdocker compose up -d


        WEEK 8 — Jenkins Freestyle Jobs (VERY BEGINNER VERSION)
Goal: Learn how to run Java & Web projects automatically using Jenkins.

 PART 1 — Install & Open Jenkins
1.Open your browser.
2.Type: http://localhost:8080
You will see the Jenkins dashboard.

 PART 2 — Create Maven Java Build Job (MavenJava_Build)
 Click “New Item”
Left side → first option.
Give a name
Example:
MavenJava_Build
Select:
Freestyle Project
Click OK.

Fill Project Details
Inside Configuration page:
Description:
Java Build demo

Add GitHub Code
Scroll to:
✔ Source Code Management → Choose Git
You will see a box:
Repository URL
Paste your Maven Java GitHub link here (example):
https://github.com/someone/maven-java-demo.git

 Build Steps (Very Important)
Scroll to Build section → click:
▶ Add Build Step → Invoke top-level Maven targets
You will now add 2 steps:
STEP A
Maven version: select your configured Maven (example: MAVEN_HOME)
Goals: clean
STEP B (again click “Add Build Step”)
Goals: install

Post-Build Actions
Scroll down → Click:
▶ Add post-build action → Archive the artifacts
Files to archive: 
Then again:
▶ Add post-build action → Build other projects
Enter: MavenJava_Test
Choose:
Trigger only if build is stable
Save the job
Click Save at bottom.


PART 3 — Create MavenJava_Test Job
Click “New Item”
Name: MavenJava_Test
Select Freestyle → OK.

 Description
Test demo

Build Environment
Scroll → tick:
✔ Delete workspace before build starts
Why?
It removes old files so test always runs fresh.

Copy the build output from previous job
Build Steps → Add Build Step → Copy artifacts from another project.
Fill:
Project name → MavenJava_Build
Build → Stable build only
Artifacts to copy: 

Add Test Step
Add Build Step → Invoke top-level Maven targets
Goals: test

Archive test results
Add Post Build Action → Archive artifacts
Files: 
Click Save

 
 PART 4 — Create Pipeline View
Steps:
1.On Jenkins dashboard, click “+” beside “All”
2.Name: MavenJava_Pipeline
3.Select: Build Pipeline View
4.Pipeline flow:
oInitial job → MavenJava_Build
5.Save.

 PART 5 — Run
1.Open pipeline view
2.Click “Run”
3.Green = success
4.Click boxes → open console

 Maven Java part DONE!

PART 6 — Repeat SAME for Maven Web Project
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

 WEEK 9 — Scripted Pipeline (BEGINNER VERSION)
Goal: Create ONE Jenkins job using Pipeline script.

 Click “New Item”
Name: ScriptedPipeline
Choose: Pipeline
Click OK.

 Scroll down to Pipeline section
Under Definition, select:
Pipeline script

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

Save

Run
Click Build now.
Take screenshots of:
Build stages
Console output
Done.

WEEK 10 — Minikube, Kubernetes, Nagios, AWS (BEGINNER VERSION)

PART 1 — Minikube
 Start Minikube
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

 
 PART 2 — Nagios in Docker
 Pull image
docker pull jasonrivers/nagios:latest
 Run Nagios
docker run --name nagiosdemo -p 8888:80 jasonrivers/nagios:latest
 Open browser
Go to:
http://localhost:8888
Login:
username: nagiosadmin
password: nagios
stopping: docker stop nagiosdemo

WEEK 11 — GitHub Webhook + Jenkins Email (SUPER SIMPLE)

PART 1 — Install ngrok
Run in CMD:
ngrok http 8080
You will see:
https://abc123.ngrok.io (just for example)
Copy this.

PART 2 — Add Webhook in GitHub
1.Open your GitHub repo → Settings → Webhooks
2.Click Add Webhook
3.Payload URL:
https://abc123.ngrok.io/github-webhook/ (make sure to add /github-webhook/ at the end of your ngrok url)
4.Content type: application/json
5.Events: ✔ Just the push event
6.Add webhook

PART 3 — Configure Jenkins job
Open your job → Configure → Build Triggers
✔ Tick: GitHub hook trigger for GITScm polling
Save.

PART 4 — Test webhook
1.Make any change to your GitHub project
2.Push the code
3.Jenkins WILL automatically start building
DONE.

PART 5 — Email Notification Setup (Beginner version)
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

 PART 6 — Add email to Job
Open your job → Configure → Post-build actions:
Add:
Editable Email Notification
Add recipients → Save → Build.
DONE.

WEEK 12 — Deploying App in AWS EC2 Using Docker

PART 1 — Create EC2 instance
1.AWS → Start Lab and click on the green-dot → EC2 → Launch Instance
2.Name: ubuntu
3.AMI: Ubuntu Server 22.04 LTS (Free tier)
4.Instance type: t2.micro
5.Create new key pair → download .pem file
6.Security group:
✔ SSH (22)
✔ HTTP (80)
7.Launch instance.

PART 2 — Connect to EC2
Go to instance → Click Connect → SSH tab
Copy:
ssh -i "your-key.pem" ubuntu@<your-public-ip>
Paste into PowerShell.
You are now inside your remote Ubuntu machine.

PART 3 — Install 
Run:
sudo apt update
sudo apt-get install docker.io
sudo apt install git
sudo apt install nano

Create a html file and push into git or what ever is given 
PART 4 — Clone your project
git clone <your GitHub link>
cd <project folder>

 
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
CTRL + O → Enter
CTRL + X

PART 6 — Build Docker image
sudo docker build -t mywebapp .

PART 7 — Run the container
sudo docker run -d -p 80:80 mywebapp

PART 8 — Open your website
Copy your EC2 public IP
Paste in browser → Your website appears 
->Stopping 
sudo docker ps -> gives container id 
sudo docker stop <container id>
 */
    }
}
