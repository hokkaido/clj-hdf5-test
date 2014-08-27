(ns clj-hdf5-test.core
  (:refer-clojure :exclude [read name])
  (:require clojure.string)
  (:import (java.io.File)
           (ch.systemsx.cisd.hdf5 HDF5Factory IHDF5SimpleReader
                                  IHDF5SimpleWriter HDF5FactoryProvider
                                  HDF5DataClass))
  (:gen-class))

(defn open
   [file]
   (let [factory (HDF5FactoryProvider/get)]
     (. factory open file)))

(defn make-int
  [acc]
  (do
    (.writeInt acc "/int/scalar" 1)
    (.writeIntArray acc "/int/array" (int-array [1 2 3 4 5 6]))
    (.writeIntMatrix acc "/int/matrix" (into-array (map int-array [[1 2] [3 4] [5 6]])))
    (.createMDArray (.int32 acc) "/int/mdarray" (int-array [10 10 10]))))

(defn make-float
  [acc]
  (do
      (.writeFloat acc "/float/scalar" 1)
      (.writeFloatArray acc "/float/array" (float-array [1 2 3 4 5 6]))
      (.writeFloatMatrix acc "/float/matrix" (into-array (map float-array [[1 2] [3 4] [5 6]])))
      (.createMDArray (.float32 acc) "/float/mdarray" (int-array [10 10 10]))))

(defn make-double
  [acc]
  (do
    (.writeDouble acc "/double/scalar" 1)
    (.writeDoubleArray acc "/double/array" (double-array [1 2 3 4 5 6]))
    (.writeDoubleMatrix acc "/double/matrix" (into-array (map double-array [[1 2] [3 4] [5 6]])))
    (.createMDArray (.float64 acc) "/double/mdarray" (int-array [10 10 10]))))

(defn make-string
  [acc]
  (do
    (.writeString acc "/string/scalar" "a")
    (.writeStringArray acc "/string/array" (into-array String ["a" "b" "c" "d" "e" "f"]))
    (.createMDArray (.string acc) "/string/matrix" 10 (int-array [10 10]))
    (.createMDArray (.string acc) "/string/mdarray" 10 (int-array [10 10 10]))))

(defn get-dataclass
  [acc path]
  (. (.getTypeInformation (.getDataSetInformation acc path)) getDataClass))

(defn get-raw-dataclass
  [acc path]
  (. (.getTypeInformation (.getDataSetInformation acc path)) getRawDataClass))

(defn get-javatype
  [acc path]
  (. (.getTypeInformation (.getDataSetInformation acc path)) tryGetJavaType))

(defn get-dimensions
  [acc path]
  (.getDimensions (.getDataSetInformation acc path)))

(defn print-type-info
  [acc path]
  (do
    (println str "Type information for: " path))
    (println str "HDF5DataClass:" (get-dataclass acc path))
    (println str "RAW HDF5DataClass:" (get-raw-dataclass acc path))
    (println str "Java Type, if available:" (get-javatype acc path))
    (println str "Dimensions" (count (get-dimensions acc path)))
    (println ""))

(defn print-info
  [acc type-name]
  (do
    (println str "Information for: " type-name)
    (println "--------------------")
    (print-type-info acc (str "/" type-name "/scalar"))
    (print-type-info acc (str "/" type-name "/array"))
    (print-type-info acc (str "/" type-name "/matrix"))
    (print-type-info acc (str "/" type-name "/mdarray"))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [f (open (clojure.java.io/file "test_base.hf5"))]
    (do
      (make-float f)
      (make-double f)
      (make-int f)
      (make-string f)
      (print-info f "float")
      (print-info f "double")
      (print-info f "int")
      (print-info f "string")
      )))