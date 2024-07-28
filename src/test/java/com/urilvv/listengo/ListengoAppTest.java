package com.urilvv.listengo;

import com.urilvv.listengo.controllers.PlaylistControllerTests;
import com.urilvv.listengo.services.MusicRecommendationServiceTests;
import com.urilvv.listengo.services.PlaylistServiceTests;
import com.urilvv.listengo.services.UserServiceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SelectClasses({
        PlaylistControllerTests.class,
        MusicRecommendationServiceTests.class,
        PlaylistServiceTests.class,
        UserServiceTests.class
})
@Suite
@SuiteDisplayName("listenGoTests")
public class ListengoAppTest {
}