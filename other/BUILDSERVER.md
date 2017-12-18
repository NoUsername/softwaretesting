

## Running a buildserver

* Run Jenkins.
* Setup "BlueOcean" plugin for fancy new UI.

## Setup project

If you're on the same host you could use e.g. file:///C:/.../testing/.git

Change "Periodically if not otherwise run" trigger to e.g. "1 minute" for demonstration purpose.

When adding project with "normal" jenkins UI:

* Enter a name
* Select "Multibranch Pipeline with defaults"
* Copy in git url
* "by default Jenkinsfile" to "by Jenkinsfile"

## Demo

Make some changes that break something, commit & wait :)
