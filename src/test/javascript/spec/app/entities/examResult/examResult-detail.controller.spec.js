'use strict';

describe('Controller Tests', function() {

    describe('ExamResult Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockExamResult, MockExam;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockExamResult = jasmine.createSpy('MockExamResult');
            MockExam = jasmine.createSpy('MockExam');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ExamResult': MockExamResult,
                'Exam': MockExam
            };
            createController = function() {
                $injector.get('$controller')("ExamResultDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:examResultUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
