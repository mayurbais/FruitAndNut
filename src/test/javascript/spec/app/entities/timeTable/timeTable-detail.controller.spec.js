'use strict';

describe('Controller Tests', function() {

    describe('TimeTable Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTimeTable, MockTimeSlot, MockClassRoomSession, MockSection;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTimeTable = jasmine.createSpy('MockTimeTable');
            MockTimeSlot = jasmine.createSpy('MockTimeSlot');
            MockClassRoomSession = jasmine.createSpy('MockClassRoomSession');
            MockSection = jasmine.createSpy('MockSection');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TimeTable': MockTimeTable,
                'TimeSlot': MockTimeSlot,
                'ClassRoomSession': MockClassRoomSession,
                'Section': MockSection
            };
            createController = function() {
                $injector.get('$controller')("TimeTableDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:timeTableUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
