'use strict';

describe('Controller Tests', function() {

    describe('RankingLevel Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRankingLevel, MockCourse;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRankingLevel = jasmine.createSpy('MockRankingLevel');
            MockCourse = jasmine.createSpy('MockCourse');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RankingLevel': MockRankingLevel,
                'Course': MockCourse
            };
            createController = function() {
                $injector.get('$controller')("RankingLevelDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:rankingLevelUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
