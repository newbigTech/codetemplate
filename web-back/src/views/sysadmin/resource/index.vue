<template>
  <imp-panel>
    <h3 class='box-title' slot='header' style='width: 100%;'>
      <el-button type='primary' icon='plus' @click='newAdd'>新增</el-button>
      <el-button type='danger' icon='delete' @click='batchDelete'>删除</el-button>
    </h3>
    <div class="icons-wrapper"><i class="fa fa-address-book" @click="selectIcon($event)"></i><i
      class="fa fa-address-book-o" @click="selectIcon($event)"></i>
    </div>
    <el-row slot='body' style='margin-bottom: 20px;' :gutter='24'>
      <el-col :span='6' :xs='24' :sm='24' :md='6' :lg='6' style='margin-bottom: 20px;'>
        <el-tree v-if="resourceTree" ref="resourceTree" :data="resourceTree" show-checkbox highlight-current :render-content="renderContent"
                 @node-click="handleNodeClick" clearable node-key="id" :props="defaultProps">
        </el-tree>
      </el-col>
      <el-col :span='18' :xs='24' :sm='24' :md='18' :lg='18'>
        <el-card class='box-card'>
          <!--<div slot='header' class='clearfix'>-->
          <!--<el-button type='primary' style='float: right;' @click='dialogFormVisible = true'><i class='el-icon-plus'></i></el-button>-->
          <!--&lt;!&ndash;<el-button type='primary' @click='editSelectedMenu' icon='edit'></el-button>&ndash;&gt;-->
          <!--&lt;!&ndash;<el-button type='primary' @click='deleteSelectedMenu' icon='delete'></el-button>&ndash;&gt;-->
          <!--</div>-->
          <div class='text item'>
            <el-form :model='form' ref='form'>
              <el-form-item label='父级' :label-width='formLabelWidth'>
                <!--<el-input v-model="form.parentId" auto-complete="off"></el-input>-->
                <el-select-tree v-model='form.parentId' :treeData='resourceTree' :propNames='defaultProps' clearable
                                placeholder='请选择父级'>
                </el-select-tree>
              </el-form-item>
              <el-form-item label='资源名称' :label-width='formLabelWidth'>
                <el-input v-model='form.name' auto-complete='off'></el-input>
              </el-form-item>
              <el-form-item label='Path' :label-width='formLabelWidth'>
                <el-input v-model='form.path' auto-complete='off'></el-input>
              </el-form-item>
              <el-form-item label='SPM' :label-width='formLabelWidth'>
                <el-input v-model='form.spm' auto-complete='off'></el-input>
              </el-form-item>
              <el-form-item label='图标' :label-width='formLabelWidth'>
                <el-input v-model='form.icon' auto-complete='off'></el-input>
              </el-form-item>
              <el-form-item label='类型' :label-width='formLabelWidth'>
                <el-radio class='radio' v-model='form.type' :label='1'>菜单</el-radio>
                <el-radio class='radio' v-model='form.type' :label='2'>按钮</el-radio>
                <el-radio class='radio' v-model='form.type' :label='3'>功能</el-radio>
              </el-form-item>
              <el-form-item label='排序' :label-width='formLabelWidth'>
                <el-slider v-model='form.sort'></el-slider>
              </el-form-item>
              <el-form-item label='重定向URL' :label-width='formLabelWidth'>
                <el-input v-model='form.redirect' auto-complete='off'></el-input>
              </el-form-item>
              <el-form-item label='后端接口' :label-width='formLabelWidth'>
                <el-input v-model='form.apis' auto-complete='off' placeholder='多个用,分割'></el-input>
              </el-form-item>
              <el-form-item label='' :label-width='formLabelWidth'>
                <el-button type='primary' @click='onSubmit' v-text="form.id ? '修改':'新增'"></el-button>
                <el-button type='danger' @click='deleteSelected' icon='delete' v-show='form.id && form.id!=null'>删除
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </imp-panel>

</template>
<script>
  import panel from '@/components/panel.vue'
  import selectTree from '@/components/selectTree.vue'
  import treeter from '@/components/treeter'
  import fetchList from '@/api/resource.js'
  export default {
    mixins: [treeter],
    components: {
      'imp-panel': panel,
      'el-select-tree': selectTree
    },
    data() {
      return {
        formLabelWidth: '100px',
        defaultProps: {
          children: 'children',
          label: 'name',
          id: 'id'
        },
        resourceTree: [],
        maxId: 700000,
        form: {
          id: null,
          parentId: null,
          name: '',
          code: '',
          type: 1,
          sort: 0,
          remarks: ''
        }
      }
    },
    methods: {
      newAdd() {
        this.form = {
          id: null,
          parentId: null,
          name: '',
          code: '',
          type: 1,
          sort: 0,
          usable: '1',
          remarks: ''
        }
      },
      /* eslint-disable */
      renderContent(h, { node, data, store }) {
        return (
          <span>
            <span>
              <span> {node.label} </span>
            </span>
          </span>
        )
      },

      deleteSelected() {
        const id = new Array(1);
        id[0] = this.form.id;
        fetch({
          url: api.SYS_RESOURCE_DELETE,
          method: 'POST',
          data: {
            ids: id
          }
        }).then(res => {
          if (res.data.code === 200) {
            this.$message('删除成功')
            this.deleteFromTree(this.resourceTree, this.form.id)
          }
        }).catch(e => {
        })
      },
      batchDelete() {
        var checkKeys = this.$refs.resourceTree.getCheckedKeys()
        if (checkKeys == null || checkKeys.length <= 0) {
          this.$message.warning('请选择要删除的资源')
          return
        }
        this.$confirm('确定删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          fetch({
            url: api.SYS_RESOURCE_DELETE,
            method: 'POST',
            data: {
              ids: checkKeys
            }
          }).then(res => {
            if (res.data.code === 200) {
              this.$message('删除成功')
              this.load()
            }
          }).catch(e => {
          })
        })
      },
      handleNodeClick(data) {
        this.form = data
      },
      onSubmit() {
        if (this.form.id == null) {
          fetch({
              url: api.SYS_RESOURCE_ADD,
              method: 'POST',
              data: this.form
            }).then(res => {
              if (res.data.code === 200) {
                this.$message('添加成功')
                this.form.id = res.data.id
                this.appendTreeNode(this.resourceTree, res.data.result)
              }
            }).catch(e => {
            })
        } else {
          fetch({
            url: api.SYS_RESOURCE_UPDATE,
            method: 'POST',
            data: this.form
          }).then(res => {
            if (res.data.code === 200) {
              this.$message('更新成功')
              this.updateTreeNode(this.resourceTree, res.data)
            }
          }).catch(e => {
          })
        }
      },
      getResourceTree() {
        fetchList()
          .then(res => {
            this.resourceTree = []
            // this.resourceTree.push(...res.data.result)
          })
      }
    },
    created() {
      this.getResourceTree()
    }
  }
</script>

<style>
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: '';
  }

  .clearfix:after {
    clear: both
  }

</style>
