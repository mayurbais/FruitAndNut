'use strict';

describe('Controller Tests', function() {

    describe('Exam Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockExam, MockCourse, MockSection, MockExamSubjects;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockExam = jasmine.createSpy('MockExam');
            MockCourse = jasmine.createSpy('MockCourse');
            MockSection = jasmine.createSpy('MockSection');
            MockExamSubjects = jasmine.createSpy('MockExamSubjects');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Exam': MockExam,
                'Course': MockCourse,
                'Section': MockSection,
                'ExamSubjects': MockExamSubjects
            };
            createController = function() {
                $injector.get('$controller')("ExamDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:examUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
