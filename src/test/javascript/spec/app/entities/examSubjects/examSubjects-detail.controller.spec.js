'use strict';

describe('Controller Tests', function() {

    describe('ExamSubjects Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockExamSubjects, MockExam, MockSubject, MockTeacher, MockRoom;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockExamSubjects = jasmine.createSpy('MockExamSubjects');
            MockExam = jasmine.createSpy('MockExam');
            MockSubject = jasmine.createSpy('MockSubject');
            MockTeacher = jasmine.createSpy('MockTeacher');
            MockRoom = jasmine.createSpy('MockRoom');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ExamSubjects': MockExamSubjects,
                'Exam': MockExam,
                'Subject': MockSubject,
                'Teacher': MockTeacher,
                'Room': MockRoom
            };
            createController = function() {
                $injector.get('$controller')("ExamSubjectsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:examSubjectsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
