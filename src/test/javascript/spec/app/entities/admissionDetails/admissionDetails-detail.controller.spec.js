'use strict';

describe('Controller Tests', function() {

    describe('AdmissionDetails Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAdmissionDetails, MockStudent, MockPrevSchoolInfo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAdmissionDetails = jasmine.createSpy('MockAdmissionDetails');
            MockStudent = jasmine.createSpy('MockStudent');
            MockPrevSchoolInfo = jasmine.createSpy('MockPrevSchoolInfo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'AdmissionDetails': MockAdmissionDetails,
                'Student': MockStudent,
                'PrevSchoolInfo': MockPrevSchoolInfo
            };
            createController = function() {
                $injector.get('$controller')("AdmissionDetailsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'try1App:admissionDetailsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
