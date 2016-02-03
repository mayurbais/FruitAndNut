'use strict';

describe('Controller Tests', function() {

    describe('ClassRoomSession Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockClassRoomSession, MockEmployee, MockSection, MockRoom, MockTimeTable;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockClassRoomSession = jasmine.createSpy('MockClassRoomSession');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockSection = jasmine.createSpy('MockSection');
            MockRoom = jasmine.createSpy('MockRoom');
            MockTimeTable = jasmine.createSpy('MockTimeTable');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ClassRoomSession': MockClassRoomSession,
                'Employee': MockEmployee,
                'Section': MockSection,
                'Room': MockRoom,
                'TimeTable': MockTimeTable
            };
            createController = function() {
                $injector.get('$controller')("ClassRoomSessionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:classRoomSessionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
