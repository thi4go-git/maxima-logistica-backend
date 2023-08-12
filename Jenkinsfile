pipeline {

   agent any;

   tools {
        jdk 'JDK_11'
        maven 'MAVEN_3.8.8'
   }

   stages {
      stage('Compilando') {
            steps {
                 sh 'chmod 777 ./mvnw'
                 sh './mvnw clean package -P production -DskipTests'
            }
      }
      stage('Testes unitarios'){
          steps {
              sh './mvnw verify'
          }
      }
      stage('Sonar Analise') {
           environment{
               scannerHome = tool 'SONAR_SCANNER'
           }
           steps {
               withSonarQubeEnv('SONAR'){
                   sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=maxima-logistica-backend -Dsonar.host.url=http://cloudtecnologia.dynns.com:9000 -Dsonar.login=60064720daace2d5578d96607f0e4ee9b4f3b98b -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/entity/**,**/dto/**,**/enums/**,**/repository/**,MavenWrapperDownloader.java,MaximaLogisticaBackendApplication.java"
               }
           }
      }
      stage('Sonar QualityGate') {
           steps {
               sleep(20)
               timeout(time: 1, unit: 'MINUTES'){
                   waitForQualityGate abortPipeline: true
               }
           }
      }
   }

   post{
       always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            script {
                if (currentBuild.result == 'FAILURE') {
                     echo "Build Com erro(s)!"
                     emailext attachLog: true, body: 'LOG:', subject: "BUILD ${BUILD_NUMBER} maxima-logistica-backend Executado com Erro(s)!", to: 'thi4go19+jenkins@gmail.com'
                } else {
                     echo "Build bem-sucedido!"
                     emailext attachLog: true, body: 'LOG:', subject: "BUILD ${BUILD_NUMBER} maxima-logistica-backend Executado com Sucesso!", to: 'thi4go19+jenkins@gmail.com'
                }
            }
       }
   }

}