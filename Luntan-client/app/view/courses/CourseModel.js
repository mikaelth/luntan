Ext.define('Luntan.view.courses.CourseModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.coursemodel',

    data: {
		current : {
			edocId : '',
			edoc : null,
			ci : null,
			cid : null,
			fcid : null
		},
		cloning: {
        	cloneDestED: null,
//        	seletcedCIsToClone : []
		},
    },
    
    stores: {
		coursegroups: 'CourseGroupStore',    	
		courses: 'CourseStore',    	
  
		extradesstore: {
			type: 'chained',
			source: 'CIDesignationStore'
		},    	
 		usedYears: {
 			type:'chained',
 			source:'EconomyDocStore',
 			sorters: [{property:'year', direction: 'DESC'}]
 		},
 		futureYears: {
 			type:'chained',
 			source:'EconomyDocStore',
			filters: [{property: 'year', value: '{current.edoc.year}', operator: '>'}, {property: 'locked', value: false, exactMatch: true}],
 			sorters: [{property:'year', direction: 'DESC'}]
 		},
		cistore: {
			type: 'chained',
			source: 'CourseInstanceStore',
			filters: [{property: 'economyDocId', value: '{current.edoc.id}', exactMatch: true}],
			sorters: [{property:'courseName', direction: 'ASC'}],
			groupField: 'courseGroup'
		},    	
		citaskstore: {
			type: 'chained',
			source: 'CourseInstanceStore',
			filters: [{property: 'economyDocId', value: '{current.edoc.id}', exactMatch: true}],
//			filters: [{property: 'economyDocId', value: '{current.edocId}', exactMatch: true}],
			sorters: [{property:'courseName', direction: 'ASC'}],
			groupField: 'courseGroup'
		},
		precedingistore: {
			type: 'chained',
			source: 'CourseInstanceStore',
			filters: [{property: 'courseId', value: '{current.cid}', exactMatch: true}],
			sorters: [{property:'ciDesignation', direction: 'ASC'}]
		},
		fmstore : {
			type: 'chained',
			source: 'FundingModelStore'
		},
		teachers : {
			type: 'chained',
			source: 'TeacherStore'
		},
		examiners : {
			type: 'chained',
			source: 'ExaminerStore',
			filters: [{property: 'courseId', value: '{current.fcid}', exactMatch: true}, {property: 'decided', value: false, exactMatch: true}]
			
		}
/*
		courses: {
			type: 'chained',
			source: 'CourseStore'
		}
 */
		    	
	},
	
	formulas: {
/* 
        workingEDoc: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{comboCurrentYear.selection.id}', //--> reference configurated on the grid view (reference: comboCurrentYear)
                deep: true
            },
            get: function(edocId) {
            	this.set('current.edocId', edocId);
                return edocId;
            }
        },
 */
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


        cloneDestEDoc: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{comboFutureYears.selection}', //--> reference configurated on the grid view (reference: comboCurrentYear)
                deep: true
            },
            get: function(cloneDestED) {
            	this.set('cloning.cloneDestED', cloneDestED);
                return cloneDestED;
            }
        },
 
 		disableEditBtns :  function (get) {
 			return get('current.edoc') === null || get('current.edoc.locked');
 		},
 		
        currentCI: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{ciList.selection}', //--> reference configurated on the grid view (reference: ciList)
                deep: true
            },
            get: function(ci) {
            	this.set('current.ci', ci);
                return ci;
            }
        },

        currentCourse: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{ciList.selection.courseId}', //--> reference configurated on the grid view (reference: ciList)
                deep: true
            },
            get: function(cid) {
            	this.set('current.cid', cid);
                return cid;
            }
 
		},
        currentFormalCourse: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{courselist.selection.id}', //--> reference configurated on the grid view (reference: ciList)
                deep: true
            },
            get: function(fcid) {
            	this.set('current.fcid', fcid);
                return fcid;
            }
 
		}
	}

});
