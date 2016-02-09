'use strict';

describe('Controller Tests', function() {

    describe('DairyDescription Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDairyDescription, MockSchoolDetails;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDairyDescription = jasmine.createSpy('MockDairyDescription');
            MockSchoolDetails = jasmine.createSpy('MockSchoolDetails');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DairyDescription': MockDairyDescription,
                'SchoolDetails': MockSchoolDetails
            };
            createController = function() {
                $injector.get('$controller')("DairyDescriptionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:dairyDescriptionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
