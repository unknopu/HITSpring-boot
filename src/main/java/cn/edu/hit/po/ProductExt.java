package cn.edu.hit.po;

import java.util.List;

public class ProductExt<T> {
    // 二级页  商品列表页
    private Integer cId;
    private Integer csId;


    // 分页有关的属性

    // 接收的当前页
    private  Integer  pageNow = 1;
    // 每页接收的条数页
    private  Integer pageSize = 12;
    // 总条数
    private Integer pageCount;
    // 总页数
    private  Integer rowCount;
    private List<T> list;

    // 接收的搜索字段
    private  String pName;


    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getCsId() {
        return csId;
    }

    public void setCsId(Integer csId) {
        this.csId = csId;
    }

    public String getpName() {
        if(this.pName == ""){
            return null;
        }else {
            return pName;
        }
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getPageNow() {
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ProductExt{" +
                "cId=" + cId +
                ", csId=" + csId +
                ", pageNow=" + pageNow +
                ", pageSize=" + pageSize +
                ", pageCount=" + pageCount +
                ", rowCount=" + rowCount +
                ", list=" + list +
                ", pName='" + pName + '\'' +
                '}';
    }


    // 分页规则  页码

    int[] arr;

    public int[] getArr(){

        // 当前页
        //  10个页面
        int pagenow = Math.min(getPageNow() , getRowCount());
        // 总页数
        int rowcount = getRowCount();

        // 起始值
        int begin = 1;
        // 终止值
        int end = 1;

        if(pagenow < 4){
            end = Math.min(rowcount , 7);
        }else{
            // 10个页   看到5页           2345678   5
            begin = Math.min(pagenow - 3 , rowcount - 6);

            //     4 5 6 7 8 9 10   1 2 3 4 5 6 7
            end = Math.min(pagenow + 3 , rowcount);
        }


        begin = Math.max(1,begin);

        // [ 长度]
        arr = new int[end - begin + 1];

        int index = 0; // 索引值

        for(int i = begin ; i <= end ; i++){
            arr[index] = i;
            index++;
        }


        return arr;


    }






}















