package me.hgj.jetpackmvvm.demo.data.bean

import java.io.Serializable

data class CollectResponse(var chapterId: Int,
                           var author: String,
                           var chapterName: String,
                           var courseId: Int,
                           var desc: String,
                           var envelopePic: String,
                           var id: Int,
                           var link: String,
                           var niceDate: String,
                           var origin: String,
                           var originId: Int,
                           var publishTime: Long,
                           var title: String,
                           var userId: Int,
                           var visible: Int,
                           var zan: Int):Serializable