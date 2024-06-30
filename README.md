----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Brief Introduction:

NUSurf is a project under NUS CP2106 Independent Software Development Project (Orbital), by Ma Yitao (E1155521@u.nus.edu), current year 1 computer engineering, and Zhang Zhuoyu (E1156600@u.nus.edu), current year 1 mathematics in 2024 summer, of which all copyrights belong to NUS school of computing.

NUSurf is an integrated web application that aims to provide a school forum for all NUS students.

This project utilizes various major and minor open source developement tools and framework, as listed below:

Front End: Node.js Vue2 and associated plugins ElementUI

Back End: Java Springboot2 (Java 1.8. jdk 8)

Database: Mysql, Redis

Connectors: Mybatis-Plus Druid

Project Management tools: Vue-cli, Maven, Navicat, 

Authors's email address: 
Ma Yitao: 2991588053@qq.com
Zhang Zhuoyu: 1208399599@qq.com

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Patch Notes:

--Alpha 1.0 version 03/06/2024
The project currently includes a login and logout function and a user profile function. To use the login and logout function, please enter the correct email and password as in your own database. You will be able to logout once you logged in. To use the profile function, after you have logged in, you can click the profile button under user, one will be able to see the email, name, and avatar as you set in the database. Please note that the 'change avatar' and 'change name' method in user profile is not working yet.

--Alpha 2.0 version 29/06/2024
On top of what is mentioned above, the project currently includes a mostly functioning post and comment function as well as "my post". To view posts and comments, login is not necessary. To post new posts and view "my posts" section, you are required to login/register first. The posts are currently separated into different sections like "Music" and "ACGN", with subsections like , for example under "ACGN", "Anime". Each subsection has its own posts with their title, author and time created shown in one line which is clickable. Each post has its own comment. Under each post, by clicking respective buttons, you can post comment to the post, which is first level comment, and post comment to first level comment, which is second level comment. There are like and share buttons but are yet to function so far.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Instruction on running the project:

In order to run the project, you may need to install environment. Most commonly-found IDEs will fulfill the job. But we do recommend some IDEs, which are what this project was developed with, to avoid unnecessary bugs. For frontend, VScode is recommended while Intellij IDEA Ultimate is recommended for backend and Mysql for database. To initiate this project, you can just fetch the latest source code from github and in frontend terminal, type "npm run serve" and click the run button in backend IDE. By clicking on the link shown in frontend terminal, you can access the website. Furthermore, users are to create their own databases for tests. Examples of a database should follow the entities in the backend Entity package, where the ones with @TableField is not required, although adding extra field is not very harmful.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Detailed discussion of source code
The mentioned files and folders can and will be changed during development while the others are not supposed to be changed.

Frontend:
node_modules/: This is the file for most dependencies are stored
public/index.html: The final html document which will be rendered upon
src/: The folder where source code is placed in
src/api/: These are encapsulated api files. These files contain functions to be exported, which utilizes the request function under src/utils/request.js and will be used in posting requests to the backend server
src/assets/: This is the folder for default picture resources 
src/router/: This is the folder of vue-router components. It defines the router for internal push between different components to be rendered at frontend, which is programming navigation
src/store/: This is the folder of vuex components, which is mainly to store user info and encapsulate async functions to post request
src/utils/: This is the folder of some encapsulated functions
src/utils/auth: This is for verification of user token
src/utils/request: This is the function that is based on axios request function to post request and verify for received response
src/views/: This is the folder where the vue components that will be rendered are written
src/views/discovery: The discovery page
src/views/homepage: The homepage
src/views/login: The login page
src/views/my: The my page
src/views/page: The post page
src/views/register: The register page
src/main.js: This is the file where the vue components are loaded
src/package.json: This is the file where the versions of all loaded dependencies are recorded. New dependency added will automatically be shown here

Backend:
.idea/: These are some IDE files and should not be touched
src/: The folder for source code
src/java/: The folder for main java codes
src/java/config: The folder for configuration of internal classes and static functions
src/java/controller: The folder for controller, where url requests are received and dispatched to service level
src/java/entity: The folder for entities in the form of java class such as user and post
src/java/interceptor: The folder for interceptor, where requests go through before, after addressed and after completed
src/java/mapper: The folder for mapping functions from existing libraries.
src/java/service: The folder for service level codes, where the detailed and actual process of request and response
src/java/utils: The folder for utils, for now only the Application exists(click the green triangle button here to start the server)
src/resources: This is the folder for non-codes
src/resources/static: This is for static resouces at backend such as pictures
src/resources/application.properties: This is the integrated file for general environment configuration
src/main: This is the folder for main code
src/test: This is the folder for unit test codes, yet to be written
pom.xml: This is the file for Maven to manage dependencies