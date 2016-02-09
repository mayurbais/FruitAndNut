'use strict';

describe('Controller Tests', function() {

    describe('BusDetails Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBusDetails, MockSchoolDetails, MockStudent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBusDetails = jasmine.createSpy('MockBusDetails');
            MockSchoolDetails = jasmine.createSpy('MockSchoolDetails');
            MockStudent = jasmine.createSpy('MockStudent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'BusDetails': MockBusDetails,
                'SchoolDetails': MockSchoolDetails,
                'Student': MockStudent
            };
            createController = function() {
                $injector.get('$controller')("BusDetailsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:busDetailsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
