// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PostService.proto

package com.examples.lib;

public interface LoadPostsResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.example.LoadPostsResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .com.example.PostProto posts = 1;</code>
   */
  java.util.List<com.examples.lib.PostProto> 
      getPostsList();
  /**
   * <code>repeated .com.example.PostProto posts = 1;</code>
   */
  com.examples.lib.PostProto getPosts(int index);
  /**
   * <code>repeated .com.example.PostProto posts = 1;</code>
   */
  int getPostsCount();
  /**
   * <code>repeated .com.example.PostProto posts = 1;</code>
   */
  java.util.List<? extends com.examples.lib.PostProtoOrBuilder> 
      getPostsOrBuilderList();
  /**
   * <code>repeated .com.example.PostProto posts = 1;</code>
   */
  com.examples.lib.PostProtoOrBuilder getPostsOrBuilder(
      int index);
}
