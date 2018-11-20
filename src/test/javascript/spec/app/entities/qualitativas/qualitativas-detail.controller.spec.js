'use strict';

describe('Controller Tests', function() {

    describe('Qualitativas Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockQualitativas;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockQualitativas = jasmine.createSpy('MockQualitativas');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Qualitativas': MockQualitativas
            };
            createController = function() {
                $injector.get('$controller')("QualitativasDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'intersepHipsterApp:qualitativasUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
