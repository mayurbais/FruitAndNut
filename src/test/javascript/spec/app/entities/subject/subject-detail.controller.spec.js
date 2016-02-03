'use strict';

describe('Controller Tests', function() {

    describe('Subject Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSubject, MockCourse, MockTeacher;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSubject = jasmine.createSpy('MockSubject');
            MockCourse = jasmine.createSpy('MockCourse');
            MockTeacher = jasmine.createSpy('MockTeacher');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Subject': MockSubject,
                'Course': MockCourse,
                'Teacher': MockTeacher
            };
            createController = function() {
                $injector.get('$controller')("SubjectDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:subjectUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
