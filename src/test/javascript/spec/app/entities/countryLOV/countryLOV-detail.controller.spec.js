'use strict';

describe('Controller Tests', function() {

    describe('CountryLOV Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCountryLOV;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCountryLOV = jasmine.createSpy('MockCountryLOV');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CountryLOV': MockCountryLOV
            };
            createController = function() {
                $injector.get('$controller')("CountryLOVDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:countryLOVUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
