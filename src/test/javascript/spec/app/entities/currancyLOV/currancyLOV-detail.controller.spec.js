'use strict';

describe('Controller Tests', function() {

    describe('CurrancyLOV Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCurrancyLOV;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCurrancyLOV = jasmine.createSpy('MockCurrancyLOV');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CurrancyLOV': MockCurrancyLOV
            };
            createController = function() {
                $injector.get('$controller')("CurrancyLOVDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:currancyLOVUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
