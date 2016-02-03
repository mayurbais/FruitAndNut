'use strict';

describe('Controller Tests', function() {

    describe('SubjectExamResult Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSubjectExamResult, MockExamSubjects;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSubjectExamResult = jasmine.createSpy('MockSubjectExamResult');
            MockExamSubjects = jasmine.createSpy('MockExamSubjects');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SubjectExamResult': MockSubjectExamResult,
                'ExamSubjects': MockExamSubjects
            };
            createController = function() {
                $injector.get('$controller')("SubjectExamResultDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:subjectExamResultUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
