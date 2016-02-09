'use strict';

describe('Controller Tests', function() {

    describe('DairyTemplate Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDairyTemplate, MockCourse, MockStudent, MockTeacher;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDairyTemplate = jasmine.createSpy('MockDairyTemplate');
            MockCourse = jasmine.createSpy('MockCourse');
            MockStudent = jasmine.createSpy('MockStudent');
            MockTeacher = jasmine.createSpy('MockTeacher');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DairyTemplate': MockDairyTemplate,
                'Course': MockCourse,
                'Student': MockStudent,
                'Teacher': MockTeacher
            };
            createController = function() {
                $injector.get('$controller')("DairyTemplateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:dairyTemplateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
