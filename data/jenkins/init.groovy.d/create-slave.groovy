#!groovy
/*
    Configure Nodes - add jnlp slave
*/
import jenkins.model.*
import hudson.slaves.*
import hudson.model.Node.Mode

def slaveName = 'jenkins_node_jnlp'

DumbSlave dumb = new DumbSlave(
    //name
    slaveName,
    //description
    'some slave hosted with docker',
    //workspace
    '/home/jenkins/',
    '8',
    Mode.EXCLUSIVE,
    // Labels
    'linux_x64',
    new JNLPLauncher(),
    RetentionStrategy.INSTANCE,
    new LinkedList()
)
Jenkins.instance.addNode(dumb)

// write secret to jenkins_home
def slaveSecret = Jenkins.instance.getNode(slaveName)?.computer?.jnlpMac
def scriptDir = new File(new File(getClass().protectionDomain.codeSource.location.path).parent).parent
def slaveSecretFile = new File("${scriptDir}/slave.secret")
slaveSecretFile.newWriter().withWriter { w ->
  w << slaveSecret
}
