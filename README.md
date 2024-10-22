### ♻ Infraestructura reutilizable
# Back-End IaC

## Instrucciones para reutilización de IaC:

- **`docker-compose.yaml`**: Se puede reutilizar tal como está.
- **`hosts.ini`**: cambiar "ec2-54-175-245-83.compute-1.amazonaws.com" por la dirección DNS pública de nuestra instancia EC2. Cambiar el valor de "ansible_ssh_key_file" por el valor que indique dónde están las nuestras keys de acceso a la instancia EC2: "./keys.pem". El "./" indica que están en el mismo directorio donde esto se ejecuta, pero no lo tienen que estar subidas en el repositorio, si no que tenemos que ejecutar en el pipeline scripts para que las copie de las variables de entorno de Gitlab y cree el archivo de las llaves cuando se tengan que utilizar.
- **`Dockerfile`**: se puede reutilizar tal como está, únicamente personalizando las imagenes que indican los FROM (dependiendo del JDK que estemos usando en el proyecto de Java, en este caso es el JDK 21).
- **`.gitlab-ci.yml`**: El primer pipeline del directorio raíz únicamente refiere a que se ejecute el pipeline que está en el directorio "Skyshop". El pipeline que está dentro del directorio SkyShop lo tenemos que personalizar de la siguiente manera: a la variable MVN_IMAGE le indicamos el valor que usamos en el primer FROM del Dockerfile (u otra que ejecute de la misma manera dependiendo la versión del JDK). Donde dice "- echo "$JUANKEYS" > juankeys.pem" ahí se crea el archivo de la llaves .pem en base al valor de la variable de Gitlab que contiene el valor de estas llaves (en esta variable de Gitlab es muy importante que copiemos el valor de la llaves rsa de nuestra instancia EC2 y las peguemos tal cual están, con saltos de línea y y comentarios y demás porque eso también es parte de las llaves), el cual debemos personalizar con el nombre de nuestras llaves. En el stage "create_image" tenemos que personalizar las líneas donde se ejecutan el docker build y el docker push, debemos indicar un repositorio al que podamos pushear la imagen de nuestro back-end. Recordar personalizar siempre que se nombra a las llaves .pem. 
- **`pom.xml`**: Debemos utilizar las siguientes dependencias de AWS si queremos utilizar el S3Service para la generación de urls pre-fiermadas temporales para que el Fornt-End pueda subir imágenes al Bucket S3; también debemos agregar una dependencia que indique un conector de base de datos dependiendo de lo que hayamos configurado en AWS RDS, en este caso, MySQL 8.0.33.
    ```pom.xml
    <dependency>
			<groupId>software.amazon.awssdk</groupId>
			<artifactId>s3</artifactId>
			<version>2.20.0</version>
		</dependency>
		<dependency>
			<groupId>software.amazon.awssdk</groupId>
			<artifactId>sts</artifactId>
			<version>2.20.0</version>
		</dependency>
    <dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.33</version>
		</dependency>
    ```
- **`application.properties`**: como valor de "spring.datasource.url" reemplazar en el valor que tiene, donde dice "mydb.c5wcu4a6u8bc.us-east-1.rds.amazonaws.com" por el endpoint que nos brinde nuestra instancia MySQL de AWS RDS (ver en detalle de la instancia). También personalizar los valores de "spring.datasource.username" y "spring.datasource.password" con los valores de username y password que le hayamos configurado al usuario admin de la base de datos RDS. También personalizar "aws.s3.bucketName" con el nombre que le hayamos puesto a nuestro bucket S3 y personalizar también el valor de la región de ser necesario.
- **`WebConfig.java`**: en este archivo cambiar el valor de "ec2-54-175-245-83.compute-1.amazonaws.com" con el valor de la dirección DNS pública de nuestra instancia EC2 (la misma que indicamos en hosts.ini).

### Variables de Entorno de Gitlab:

- #### Heredadas del grupo de repositorios:
  - **`JUANKEYS`**: valor de nuestras llaves .pem. 
  - **`CI_REGISTRY_USER`**: nuestro nombre de usuario de Docker Hub.
  - **`CI_REGISTRY_PASSWORD`**: nuestra contraseña de Docker Hub.
  - **`CI_REGISTRY`**: "docker.io" como valor, sin comillas. 
  - **`AWS_SECRET_ACCESS_KEY`**: nuestra llave de acceso secreta de AWS.
  - **`AWS_ACCESS_KEY_ID`**: nuestro id de llave de acceso de AWS.

- #### De este repositorio específico:
  - **`DB_ENDPOINT`**: endpoint de nuestra base de datos de AWS RDS.
  - **`DB_IDENTIFIER`**: identificador de nuestra base de datos de AWS RDS.
  - **`DB_PASSWORD`**: contraseña de usuario admin de nuestra base de datos de AWS RDS.
  - **`DB_USERNAME`**: nombre de usuario de usuario admin de nuestra base de datos de AWS RDS.
  - **`DB_PORT`**: puerto de nuestra base de datos de AWS RDS (3306 si es MySQL).

  (algunas de estas variables no se usan pero pueden utilizarse para hacer dinámica la inyección de algunos valores en el application-secrets.properties mediante scripts en el pipeline)

  Esta IaC se complementa con la infraestructura de Terraform de (este repositorio)[https://github.com/juancruzmarzetti/full-aws-iac] y tambíen con el Front-End de (este otro repositorio)[https://github.com/juancruzmarzetti/react-vite-iac-aws]
---

### ♻ Reusable Infrastructure
# Back-End IaC

## Instructions for IaC Reuse:

- **`docker-compose.yaml`**: It can be reused as is.
- **`hosts.ini`**: Replace "ec2-54-175-245-83.compute-1.amazonaws.com" with the public DNS address of your EC2 instance. Change the "ansible_ssh_key_file" value to the path of your EC2 instance access keys: "./keys.pem". The "./" indicates they are in the same directory where this is executed, but they should not be uploaded to the repository. Instead, you should execute pipeline scripts that copy them from GitLab's environment variables and create the key file when needed.
- **`Dockerfile`**: It can be reused as is, only customizing the images that define the FROM instructions (depending on the JDK used in the Java project, in this case, JDK 21).
- **`.gitlab-ci.yml`**: The main pipeline in the root directory only refers to the execution of the pipeline in the "Skyshop" directory. We need to customize the pipeline inside the SkyShop directory as follows: set the value of the MVN_IMAGE variable to the one used in the first FROM of the Dockerfile (or another that runs similarly depending on the JDK version). Where it says `- echo "$JUANKEYS" > juankeys.pem`, the `.pem` key file is created based on the GitLab variable containing the key value (it is essential to paste the EC2 instance's RSA key into this GitLab variable, including line breaks and comments, as these are part of the key). In the "create_image" stage, customize the docker build and docker push lines by specifying a repository where the back-end image can be pushed. Always remember to personalize the `.pem` key names.
- **`pom.xml`**: The following AWS dependencies must be used if we want to use the S3Service for generating temporary pre-signed URLs to allow the Front-End to upload images to the S3 Bucket. Additionally, we need to add a database connector dependency depending on what has been configured in AWS RDS, in this case, MySQL 8.0.33.
    ```pom.xml
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>s3</artifactId>
        <version>2.20.0</version>
    </dependency>
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>sts</artifactId>
        <version>2.20.0</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
    ```
- **`application.properties`**: Replace the "spring.datasource.url" value, where it says "mydb.c5wcu4a6u8bc.us-east-1.rds.amazonaws.com" with the endpoint provided by your AWS RDS MySQL instance (check the instance details). Also, personalize the "spring.datasource.username" and "spring.datasource.password" values with the username and password configured for the RDS admin user. Additionally, customize "aws.s3.bucketName" with the name of your S3 bucket and adjust the region value if necessary.
- **`WebConfig.java`**: In this file, change the value of "ec2-54-175-245-83.compute-1.amazonaws.com" to the public DNS address of your EC2 instance (the same one used in hosts.ini).

### GitLab Environment Variables:

- #### Inherited from the repository group:
  - **`JUANKEYS`**: value of our `.pem` keys.
  - **`CI_REGISTRY_USER`**: our Docker Hub username.
  - **`CI_REGISTRY_PASSWORD`**: our Docker Hub password.
  - **`CI_REGISTRY`**: "docker.io" as the value, without quotes.
  - **`AWS_SECRET_ACCESS_KEY`**: our AWS secret access key.
  - **`AWS_ACCESS_KEY_ID`**: our AWS access key ID.

- #### From this specific repository:
  - **`DB_ENDPOINT`**: AWS RDS database endpoint.
  - **`DB_IDENTIFIER`**: AWS RDS database identifier.
  - **`DB_PASSWORD`**: admin user password for the AWS RDS database.
  - **`DB_USERNAME`**: admin user name for the AWS RDS database.
  - **`DB_PORT`**: port for the AWS RDS database (3306 for MySQL).

  (Some of these variables might not be used but can be included to dynamically inject values into `application-secrets.properties` via pipeline scripts.)

  This IaC is complemented by the Terraform infrastructure from [this repository](https://github.com/juancruzmarzetti/full-aws-iac) and also by the Front-End from [this other repository](https://github.com/juancruzmarzetti/react-vite-iac-aws).
