'use strict';

describe('Controller Tests', function() {

    describe('Attendance Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAttendance, MockIrisUser, MockSection, MockCourse;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAttendance = jasmine.createSpy('MockAttendance');
            MockIrisUser = jasmine.createSpy('MockIrisUser');
            MockSection = jasmine.createSpy('MockSection');
            MockCourse = jasmine.createSpy('MockCourse');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Attendance': MockAttendance,
                'IrisUser': MockIrisUser,
                'Section': MockSection,
                'Course': MockCourse
            };
            createController = function() {
                $injector.get('$controller')("AttendanceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:attendanceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
