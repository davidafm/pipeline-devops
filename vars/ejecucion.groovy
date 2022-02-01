def call(){
  pipeline {
      agent any
      environment {
          NEXUS_USER         = credentials('NEXUS-USER')
          NEXUS_PASSWORD     = credentials('NEXUS-PASS')
      }
      parameters {
          choice(
              name:'compileTool',
              choices: ['Maven', 'Gradle'],
              description: 'Seleccione herramienta de compilación'
          )
      }
      stages {
          stage("Pipeline"){
              steps {
                  script{
                    switch(params.compileTool)
                      {
                          case 'Maven':
                              //compilar maven
                              //def ejecucion = load 'maven.groovy'
                              //ejecucion.call()
                              maven.call()
                          break;
                          case 'Gradle':
                              //compilar gradle
                              //def ejecucion = load 'gradle.groovy'
                              //ejecucion.call()
                              gradle.call()
                          break;
                      }
                  }
              }
              post{
          success{
            slackSend color: 'good', message: "[David Figueroa] [${JOB_NAME}] [${BUILD_TAG}] Ejecucion Exitosa", teamDomain: 'dipdevopsusac-tr94431', tokenCredentialId: 'token-slack'
          }
          failure{
            slackSend color: 'danger', message: "[David Figueroa] [${env.JOB_NAME}] [${BUILD_TAG}] Ejecucion fallida en stage [${env.TAREA}]", teamDomain: 'dipdevopsusac-tr94431', tokenCredentialId: 'token-slack'
          }
        }
          }
      }
  }
}
return this;