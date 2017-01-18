
export CORBA_HOME=/home/commun_depinfo/enseignants/singhoff/JACORB

export ANT_HOME=$CORBA_HOME/apache-ant-1.6.5
export JAVA_HOME=$CORBA_HOME/jdk1.6.0_21
export JACORB_HOME=$CORBA_HOME/JacORB

export JAVA_CMD=java

export PATH=$ANT_HOME/bin:$JAVA_HOME/bin:$PATH
export PATH=$JACORB_HOME/bin:$JACORB_HOME/etc:$JACORB_HOME/lib:$JACORB_HOME/classes:$JACORB_HOME/src:$PATH

export LD_LIBRARY_PATH=$JACORB_HOME/libs
export LD_LIBRARY_PATH=$CORBA_HOME/libs

export CLASSPATH=$JACORB_HOME/classes:$JACORB_HOME/lib:$ANT_HOME/lib:$JAVA_HOME/lib:.:classes






