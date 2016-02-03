'use strict';

describe('Controller Tests', function() {

    describe('GradingLevel Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockGradingLevel, MockCourse;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockGradingLevel = jasmine.createSpy('MockGradingLevel');
            MockCourse = jasmine.createSpy('MockCourse');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'GradingLevel': MockGradingLevel,
                'Course': MockCourse
            };
            createController = function() {
                $injector.get('$controller')("GradingLevelDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:gradingLevelUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
