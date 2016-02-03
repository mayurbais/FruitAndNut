'use strict';

describe('Controller Tests', function() {

    describe('ModuleLOV Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockModuleLOV;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockModuleLOV = jasmine.createSpy('MockModuleLOV');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ModuleLOV': MockModuleLOV
            };
            createController = function() {
                $injector.get('$controller')("ModuleLOVDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:moduleLOVUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
