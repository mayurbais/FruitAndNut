'use strict';

describe('Controller Tests', function() {

    describe('TimeSlot Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTimeSlot, MockTimeTable;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTimeSlot = jasmine.createSpy('MockTimeSlot');
            MockTimeTable = jasmine.createSpy('MockTimeTable');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TimeSlot': MockTimeSlot,
                'TimeTable': MockTimeTable
            };
            createController = function() {
                $injector.get('$controller')("TimeSlotDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:timeSlotUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
