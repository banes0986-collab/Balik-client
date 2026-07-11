#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links instead of relying on the value of $0
PRG="$0"
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`/"$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MSYS* )
    msys=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

CLK_TCK=100
if $darwin ; then
    CLK_TCK=100
fi

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/bin/java" ] ; then
        # Available 'java' executable there.
        JAVACMD="$JAVA_HOME/bin/java"
    else
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase the maximum file descriptors if we can.
if [ "$MAX_FD" = "maximum" ] || [ "$MAX_FD" = "max" ] ; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ $? -eq 0 ] ; then
        ulimit -n $MAX_FD_LIMIT
    fi
fi

# For Cygwin, switch paths to Windows format before running java
if $cygwin ; then
    APP_HOME=`cygpath --path --windows "$APP_HOME"`
    CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
    JAVACMD=`cygpath --unix "$JAVACMD"`

    # We build the pattern for arguments to be converted via cygpath
    ROOTDIRSANDDRIVES="(/|[a-zA-Z]:)"
    CPSUBST="([^;]+)"
    CHGPARAM="-args"
    if [ $# -gt 0 ] ; then
        while [ $# -gt 0 ] ; do
            case "$1" in
                -c)
                    CHGPARAM="-c"
                    ;;
                *)
                    if [ "$CHGPARAM" = "-args" ] ; then
                        ARGS="$ARGS $1"
                    else
                        ARGS="$ARGS $1"
                    fi
                    ;;
            esac
            shift
        done
    fi
fi

# Collect all arguments for the java command;
# handle whitespace in paths properly.
#
# (The code below is a bit tricky, but it's the only way to do it
# in older sh versions that don't have arrays.)
#
# Split arguments by newline.
# If an argument contains a space, it will be quoted when passed to java.
# If an argument contains a newline, it will be split into multiple arguments.
# This is a limitation of this script, but it's better than splitting on space.
#
# The IFS variable is used to split the arguments.
# The default value of IFS is space, tab, newline.
# We change it to newline only.
#
# We also save the original value of IFS so we can restore it later.
#
# Note: This code does not handle arguments that contain a newline.
# If you need to pass arguments with newlines, you should use a better shell.
#
# See: https://github.com/gradle/gradle/issues/3147
#
#
# Save original IFS
#
# Change IFS to newline
#
# Build java arguments
#
# Restore original IFS
#
# Run java command

# Escape application args
save () {
    for i do printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;$s/\$/'/"; done
}
if [ "$#" -gt 0 ] ; then
    APP_ARGS=`save "$@"`
else
    APP_ARGS=""
fi

# Ensure that the gradle-wrapper.jar exists
if [ ! -e "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" ] ; then
     mkdir -p "$APP_HOME/gradle/wrapper"
     echo "Downloading gradle-wrapper.jar..."
     curl -sLo "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" "https://raw.githubusercontent.com/gradle/gradle/v7.4.2/gradle/wrapper/gradle-wrapper.jar"
fi

# Execute Gradle
eval exec '"$JAVACMD"' '"$DEFAULT_JVM_OPTS"' '-classpath' '"$APP_HOME/gradle/wrapper/gradle-wrapper.jar"' 'org.gradle.wrapper.GradleWrapperMain' "$APP_ARGS"
