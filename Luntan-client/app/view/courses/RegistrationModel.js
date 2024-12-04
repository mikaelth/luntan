Ext.define('Luntan.view.courses.RegistrationModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.registrationmodel',

    data: {
		current : {
			edoc: null,
			reg : null,
			cbase: null,
			ict: null,
			icbId: 0
		},
		regDeptsValues: [{dept: "IBG", value: true}, {dept: "Annan institution", value: false}]
    },

    stores: {
		depts: 'DepartmentStore',

		ictKinds: 'ICTKindStore',

 		usedYears: {
 			type:'chained',
 			source:'EconomyDocStore',
 			sorters: [{property:'year', direction: 'DESC'}]
 		},

		registrations : {
			type: 'chained',
			source: 'IndCourseRegStore',
			filters: [
				{property: 'economyDocId', value: '{current.edoc.id}', exactMatch: true}
			],
			sorters: [
				{property:'courseDesignation', direction: 'ASC'},
				{property:'studentName', direction: 'ASC'}							
			]
		},

		regsincreds : {
			type: 'chained',
			source: 'IndCourseRegStore',
			filters: [
				{property: 'creditBasisRecId', value: '{current.cbase.id}', exactMatch: true}
			],
			sorters: [{property:'registrationDate', direction: 'DESC'}]
		},

		teachers : {
			type: 'chained',
			source: 'TeacherStore',
			sorters: [{property:'name', direction: 'ASC'}]
		},

		icis : {
			type: 'chained',
			source: 'CourseInstanceStore',
			filters: [
 				{property: 'individualYearlyCourse', value: true, exactMatch: true},
				{property: 'economyDocId', value: '{current.edoc.id}', exactMatch: true},
				{property: 'registrationValid', value: false, exactMatch: true}
		],
			sorters: [
				{property:'courseDesignation', direction: 'ASC'},
				]
		},

		icbs : {
			type: 'chained',
			source: 'CourseInstanceStore',
			filters: [
				{property: 'id', value: '{current.icbId}', exactMatch: true}
			],
			sorters: [
				{property:'courseDesignation', direction: 'ASC'},
			]
		},

		icts : {
			type: 'chained',
			source: 'IndCourseTeacherStore',
			filters: [
 				{property: 'assignmentId', value: '{current.reg.id}', exactMatch: true},
			],
		},

 		credbasis : {
			type: 'chained',
			source: 'IndCourseCreditBasisStore',
			filters: [
			],
		},
		regDept : {
			autolaod: true,
			proxy: {
				type: 'memory',
				reader: {
					type: 'json',
					rootProperty: 'regDeptsValues'
				 },
			 },
			fields: [
			   {name: 'dept', type: 'string'},
			   {name: 'value', type: 'boolean'}
			]
		}

	},

	formulas: {

        workingEDoc: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{comboCurrentYear.selection}', //--> reference configurated on the grid view (reference: comboCurrentYear)
                deep: true
            },
            get: function(edoc) {
            	this.set('current.edoc', edoc);
                return edoc;
            }
        },
        currentReg: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{coursereglist.selection}', //--> reference configurated on the grid view (reference: ciList)
                deep: true
            },
            get: function(reg) {
            	this.set('current.reg', reg);
                return reg;
            }
        },

        currentICB: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{coursereglist.selection.courseInstanceId}', //--> reference configurated on the grid view (reference: ciList)
                deep: true
            },
            get: function(icbId) {
            	this.set('current.icbId', icbId);
                return icbId;
            }
        },

       currentCBase: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{credbaselist.selection}', //--> reference configurated on the grid view (reference: credbaselist)
                deep: true
            },
            get: function(cbase) {
            	this.set('current.cbase', cbase);
                return cbase;
            }
        },

 		disableEditBtns :  function (get) {
 			return get('current.edoc') === null;
 		},

        currentICT: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{ictlist.selection}', //--> reference configurated on the grid view (reference: ictList)
                deep: true
            },
            get: function(ict) {
            	this.set('current.ict', ict);
                return ict;
            }
        }
 	}

});
