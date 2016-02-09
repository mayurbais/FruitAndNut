'use strict';

describe('Controller Tests', function() {

    describe('DairyEntry Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDairyEntry, MockCourse, MockTeacher, MockSection;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDairyEntry = jasmine.createSpy('MockDairyEntry');
            MockCourse = jasmine.createSpy('MockCourse');
            MockTeacher = jasmine.createSpy('MockTeacher');
            MockSection = jasmine.createSpy('MockSection');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DairyEntry': MockDairyEntry,
                'Course': MockCourse,
                'Teacher': MockTeacher,
                'Section': MockSection
            };
            createController = function() {
                $injector.get('$controller')("DairyEntryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:dairyEntryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
