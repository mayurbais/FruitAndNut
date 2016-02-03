'use strict';

describe('Controller Tests', function() {

    describe('LanguageLOV Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLanguageLOV;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLanguageLOV = jasmine.createSpy('MockLanguageLOV');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LanguageLOV': MockLanguageLOV
            };
            createController = function() {
                $injector.get('$controller')("LanguageLOVDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:languageLOVUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
