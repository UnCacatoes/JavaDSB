node{
  stage ('build_Project') {
    if(isUnix()){
    sh './gradlew build --info'
    }
    else{
      bat './gradlew build --info'
    }
  }
}
