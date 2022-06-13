#!/usr/bin/env bash
sbt validate
sbt -Dplay.http.router=testOnlyDoNotUseInAppConf.Routes "run 9590"