'use strict';

describe('Controller Tests', function() {

    describe('Session Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSession, MockEmployee, MockSection, MockTimeTable;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSession = jasmine.createSpy('MockSession');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockSection = jasmine.createSpy('MockSection');
            MockTimeTable = jasmine.createSpy('MockTimeTable');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Session': MockSession,
                'Employee': MockEmployee,
                'Section': MockSection,
                'TimeTable': MockTimeTable
            };
            createController = function() {
                $injector.get('$controller')("SessionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:sessionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
