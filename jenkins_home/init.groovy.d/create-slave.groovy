#!groovy
/*
    Configure Nodes - add jnlp slave
*/
import jenkins.model.*
import hudson.slaves.*
import hudson.model.Node.Mode

def slave = [
    name: 'jenkins_node_jnlp',
    description: 'some slave hosted with docker',
    workspace: '/home/jenkins/',
    buildExecutors: 8,
    labels: 'linux_x64'
]

DumbSlave dumb = new DumbSlave(slave.name, slave.description, slave.workspace, "${slave.buildExecutors}",
    Mode.NORMAL, slave.labels, new JNLPLauncher(), RetentionStrategy.INSTANCE, new LinkedList())
Jenkins.instance.addNode(dumb)

// write secret to jenkins_home
def slaveSecret = Jenkins.instance.getNode(slave.name)?.computer?.jnlpMac
def scriptDir = new File(new File(getClass().protectionDomain.codeSource.location.path).parent).parent
def slaveSecretFile = new File("${scriptDir}/slave.secret")
slaveSecretFile.newWriter().withWriter { w ->
  w << slaveSecret
}
