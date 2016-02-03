'use strict';

describe('Controller Tests', function() {

    describe('EndDate Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEndDate, MockTimeSlot, MockClassRoomSession, MockSection;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEndDate = jasmine.createSpy('MockEndDate');
            MockTimeSlot = jasmine.createSpy('MockTimeSlot');
            MockClassRoomSession = jasmine.createSpy('MockClassRoomSession');
            MockSection = jasmine.createSpy('MockSection');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'EndDate': MockEndDate,
                'TimeSlot': MockTimeSlot,
                'ClassRoomSession': MockClassRoomSession,
                'Section': MockSection
            };
            createController = function() {
                $injector.get('$controller')("EndDateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:endDateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
