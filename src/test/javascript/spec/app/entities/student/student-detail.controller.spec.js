'use strict';

describe('Controller Tests', function() {

    describe('Student Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStudent, MockStudentCategory, MockCourse, MockSection, MockIrisUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStudent = jasmine.createSpy('MockStudent');
            MockStudentCategory = jasmine.createSpy('MockStudentCategory');
            MockCourse = jasmine.createSpy('MockCourse');
            MockSection = jasmine.createSpy('MockSection');
            MockIrisUser = jasmine.createSpy('MockIrisUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Student': MockStudent,
                'StudentCategory': MockStudentCategory,
                'Course': MockCourse,
                'Section': MockSection,
                'IrisUser': MockIrisUser
            };
            createController = function() {
                $injector.get('$controller')("StudentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:studentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
