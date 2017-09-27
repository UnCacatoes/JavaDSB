node{
  stage ('build_Project') {
    if(isUnix()){
    sh 'gradle build --info'
    }
    else{
      bat 'gradle build --info'
    }
  }
}
